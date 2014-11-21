package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Purchase;
import javax.persistence.EntityManager;
import java.util.Date;
import api.rest.dto.NestedMemberDTO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchaseDTO implements Serializable
{

   private Long id;
   private String lastName;
   private String firstName;
   private String address;
   private String additionalInformation;
   private String zipCode;
   private String city;
   private String country;
   private Float total;
   private java.util.Date datePurchase;
   private NestedMemberDTO member;

   public PurchaseDTO()
   {
   }

   public PurchaseDTO(final Purchase entity)
   {
      if (entity != null)
      {
         this.id = entity.getId();
         this.lastName = entity.getLastName();
         this.firstName = entity.getFirstName();
         this.address = entity.getAddress();
         this.additionalInformation = entity.getAdditionalInformation();
         this.zipCode = entity.getZipCode();
         this.city = entity.getCity();
         this.country = entity.getCountry();
         this.total = entity.getTotal();
         this.datePurchase = entity.getDatePurchase();
         this.member = new NestedMemberDTO(entity.getMember());
      }
   }

   public Purchase fromDTO(Purchase entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Purchase();
      }
      entity.setLastName(this.lastName);
      entity.setFirstName(this.firstName);
      entity.setAddress(this.address);
      entity.setAdditionalInformation(this.additionalInformation);
      entity.setZipCode(this.zipCode);
      entity.setCity(this.city);
      entity.setCountry(this.country);
      entity.setTotal(this.total);
      entity.setDatePurchase(this.datePurchase);
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

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName(final String lastName)
   {
      this.lastName = lastName;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getAddress()
   {
      return this.address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
   }

   public String getAdditionalInformation()
   {
      return this.additionalInformation;
   }

   public void setAdditionalInformation(final String additionalInformation)
   {
      this.additionalInformation = additionalInformation;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getCity()
   {
      return this.city;
   }

   public void setCity(final String city)
   {
      this.city = city;
   }

   public String getCountry()
   {
      return this.country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   public Float getTotal()
   {
      return this.total;
   }

   public void setTotal(final Float total)
   {
      this.total = total;
   }

   public Date getDatePurchase()
   {
      return this.datePurchase;
   }

   public void setDatePurchase(final java.util.Date datePurchase)
   {
      this.datePurchase = datePurchase;
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