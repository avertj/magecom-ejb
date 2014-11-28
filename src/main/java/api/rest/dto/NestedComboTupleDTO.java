package api.rest.dto;

import java.io.Serializable;
import persistance.entity.tuple.ComboTuple;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedComboTupleDTO implements Serializable
{

   private Long id;
   private int quantity;

   public NestedComboTupleDTO()
   {
   }

   public NestedComboTupleDTO(final ComboTuple entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.quantity = entity.getQuantity();
      }
   }

   public ComboTuple fromDTO(ComboTuple entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new ComboTuple();
      }
      if (this.id != null)
      {
         TypedQuery<ComboTuple> findByIdQuery = em
               .createQuery(
                     "SELECT DISTINCT c FROM ComboTuple c WHERE c.id = :entityId",
                     ComboTuple.class);
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