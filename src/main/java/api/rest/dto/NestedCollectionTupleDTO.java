package api.rest.dto;

import java.io.Serializable;
import persistance.entity.tuple.CollectionTuple;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedCollectionTupleDTO implements Serializable
{

   private Long id;
   private int quantity;

   public NestedCollectionTupleDTO()
   {
   }

   public NestedCollectionTupleDTO(final CollectionTuple entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.quantity = entity.getQuantity();
      }
   }

   public CollectionTuple fromDTO(CollectionTuple entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new CollectionTuple();
      }
      if (this.id != null)
      {
         TypedQuery<CollectionTuple> findByIdQuery = em
               .createQuery(
                     "SELECT DISTINCT c FROM CollectionTuple c WHERE c.id = :entityId",
                     CollectionTuple.class);
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