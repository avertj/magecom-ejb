package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Combo;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import api.rest.dto.ColorDTO;

public class NestedComboDTO implements Serializable
{

   private Long id;
   private String name;
   private String description;
   private java.util.Date dateCreation;
   private ColorDTO color;

   public NestedComboDTO()
   {
   }

   public NestedComboDTO(final Combo entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.name = entity.getName();
         this.description = entity.getDescription();
         this.dateCreation = entity.getDateCreation();
         this.color = new ColorDTO(entity.getColor());
      }
   }

   public Combo fromDTO(Combo entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Combo();
      }
      if (this.id != null)
      {
         TypedQuery<Combo> findByIdQuery = em.createQuery(
               "SELECT DISTINCT c FROM Combo c WHERE c.id = :entityId",
               Combo.class);
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
      entity.setName(this.name);
      entity.setDescription(this.description);
      entity.setDateCreation(this.dateCreation);
      if (this.color != null)
      {
         entity.setColor(this.color.fromDTO(entity.getColor(), em));
      }
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

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public Date getDateCreation()
   {
      return this.dateCreation;
   }

   public void setDateCreation(final java.util.Date dateCreation)
   {
      this.dateCreation = dateCreation;
   }

   public ColorDTO getColor()
   {
      return this.color;
   }

   public void setColor(final ColorDTO color)
   {
      this.color = color;
   }
}