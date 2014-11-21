package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Combo;
import javax.persistence.EntityManager;
import java.util.Date;
import api.rest.dto.ColorDTO;
import api.rest.dto.NestedMemberDTO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ComboDTO implements Serializable
{

   private Long id;
   private String name;
   private String description;
   private java.util.Date dateCreation;
   private ColorDTO color;
   private NestedMemberDTO member;

   public ComboDTO()
   {
   }

   public ComboDTO(final Combo entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.name = entity.getName();
         this.description = entity.getDescription();
         this.dateCreation = entity.getDateCreation();
         this.color = new ColorDTO(entity.getColor());
         this.member = new NestedMemberDTO(entity.getMember());
      }
   }

   public Combo fromDTO(Combo entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Combo();
      }
      entity.setName(this.name);
      entity.setDescription(this.description);
      entity.setDateCreation(this.dateCreation);
      if (this.color != null)
      {
         entity.setColor(this.color.fromDTO(entity.getColor(), em));
      }
      if (this.member != null)
      {
         entity.setMember(this.member.fromDTO(entity.getMember(), em));
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

   public NestedMemberDTO getMember()
   {
      return this.member;
   }

   public void setMember(final NestedMemberDTO member)
   {
      this.member = member;
   }
}