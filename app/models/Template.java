package models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Entity
public class Template extends Model
{

    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(256)
    // @Unique
    public String name;

    @ManyToOne
    public Category category;

    public String categoryName;

    @Constraints.Required
    @Enumerated(EnumType.STRING)
    public DiskFormatType diskFormatType;

    @Enumerated(EnumType.STRING)
    public EthernetDriver ethernetDriver;

    @Constraints.MaxLength(1024)
    public String description;

    @Required
    // @Unique
    public String diskFilePath;

    /**in bytes*/
    public Long diskFileSize;

    public Integer cpu = 2;

    /**in Mb*/
    public Long ram;

    /**in Mb*/
    public Long hd;

    public String iconPath;

    public String userMail;


    public enum EthernetDriver
    {
        E1000, PCNet32, VMXNET3;


        public static List<String> options()
        {
            return new LinkedList<String>(Collections2.transform(Arrays.asList(EthernetDriver.values()), new Function<EthernetDriver, String>()
            {
                @Override
                public String apply(final EthernetDriver input)
                {
                    return input.name();
                }
            }));
        }
    }

    public enum DiskFormatType
    {
     
        /* 0 */UNKNOWN("http://unknown"),
        /* 1 */RAW("http://raw"),
        /* 2 */INCOMPATIBLE("http://incompatible"),
        /* 3 */VMDK_STREAM_OPTIMIZED(
            "http://www.vmware.com/technical-resources/interfaces/vmdk_access.html#streamOptimized"),
        /* 4 */VMDK_FLAT(
            "http://www.vmware.com/technical-resources/interfaces/vmdk_access.html#monolithic_flat"),
        /* 5 */VMDK_SPARSE(
            "http://www.vmware.com/technical-resources/interfaces/vmdk_access.html#monolithic_sparse"),
        /* 6 */VHD_FLAT(
            "http://technet.microsoft.com/en-us/virtualserver/bb676673.aspx#monolithic_flat"),
        /* 7 */VHD_SPARSE(
            "http://technet.microsoft.com/en-us/virtualserver/bb676673.aspx#monolithic_sparse"),
        /* 8 */VDI_FLAT("http://forums.virtualbox.org/viewtopic.php?t=8046#monolithic_flat"),
        /* 9 */VDI_SPARSE("http://forums.virtualbox.org/viewtopic.php?t=8046#monolithic_sparse"),
        /* 10 */QCOW2_FLAT("http://people.gnome.org/~markmc/qcow-image-format.html#monolithic_flat"),
        /* 11 */QCOW2_SPARSE(
            "http://people.gnome.org/~markmc/qcow-image-format.html#monolithic_sparse");

        private final String url;

        private DiskFormatType(final String url)
        {
            this.url = url;
        }

        public String url()
        {
            return url;
        }

        @Override
        public String toString()
        {
            return this.name();
        }
        
        public static List<String> options()
        {
            
            return new LinkedList<String>(Collections2.transform(Arrays.asList(DiskFormatType.values()), new Function<DiskFormatType, String>()
            {
                @Override
                public String apply(final DiskFormatType input)
                {
                    return input.name();
                }
            }));
        }
        
    }

    /**
     * Generic query helper for entity Template with id Long
     */
    public static Finder<Long, Template> find = new Finder<Long, Template>(Long.class,
        Template.class);

    /**
     * Return a page of Template
     * 
     * @param page Page to display
     * @param pageSize Number of Templates per page
     * @param sortBy Template property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Template> page(final int page, final int pageSize, final String sortBy,
        final String order, final String filter)
    {
        return find.where().ilike("name", "%" + filter + "%").orderBy(sortBy + " " + order)
            .fetch("category").findPagingList(pageSize).getPage(page);
    }

}
