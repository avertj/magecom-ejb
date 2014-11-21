package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Color;
import javax.persistence.EntityManager;

public class ColorDTO implements Serializable
{

   private Boolean red;
   private Boolean green;
   private Boolean blue;
   private Boolean black;
   private Boolean white;

   public ColorDTO()
   {
   }

   public ColorDTO(final Color entity)
   {
      if (entity != null)
      {
         this.red = entity.getRed();
         this.green = entity.getGreen();
         this.blue = entity.getBlue();
         this.black = entity.getBlack();
         this.white = entity.getWhite();
      }
   }

   public Color fromDTO(Color entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Color();
      }
      entity.setRed(this.red);
      entity.setGreen(this.green);
      entity.setBlue(this.blue);
      entity.setBlack(this.black);
      entity.setWhite(this.white);
      return entity;
   }

   public Boolean getRed()
   {
      return this.red;
   }

   public void setRed(final Boolean red)
   {
      this.red = red;
   }

   public Boolean getGreen()
   {
      return this.green;
   }

   public void setGreen(final Boolean green)
   {
      this.green = green;
   }

   public Boolean getBlue()
   {
      return this.blue;
   }

   public void setBlue(final Boolean blue)
   {
      this.blue = blue;
   }

   public Boolean getBlack()
   {
      return this.black;
   }

   public void setBlack(final Boolean black)
   {
      this.black = black;
   }

   public Boolean getWhite()
   {
      return this.white;
   }

   public void setWhite(final Boolean white)
   {
      this.white = white;
   }
}