package api.rest.dto;

import java.io.Serializable;
import persistance.entity.tuple.PurchaseTuple;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedPurchaseTupleDTO implements Serializable
{

   private Long id;
   private int quantity;

   public NestedPurchaseTupleDTO()
   {
   }

   public NestedPurchaseTupleDTO(final PurchaseTuple entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.quantity = entity.getQuantity();
      }
   }

   public PurchaseTuple fromDTO(PurchaseTuple entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new PurchaseTuple();
      }
      if (this.id != null)
      {
         TypedQuery<PurchaseTuple> findByIdQuery = em
               .createQuery(
                     "SELECT DISTINCT p FROM PurchaseTuple p WHERE p.id = :entityId",
                     PurchaseTuple.class);
         findByIdQuery.setParameter("entityId", this.id);
         try
         {
            entity = findByIdQuery.getSingleResult();
         }
         catch (javax.persistence.NoResultException nre)
         {
            entity = null;
         }
         return entity;
      }
      entity.setQuantity(this.quantity);
      entity = em.merge(entity);
      return entity;
   }

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getQuantity()
   {
      return this.quantity;
   }

   public void setQuantity(final int quantity)
   {
      this.quantity = quantity;
   }
}