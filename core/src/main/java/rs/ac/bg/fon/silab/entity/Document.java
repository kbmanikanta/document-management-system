package rs.ac.bg.fon.silab.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id")
    private List<DocumentItem> documentItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    private Template template;

    public Document() {}

    public Document(String name, List<DocumentItem> documentItems, Template template) {
        this.name = name;
        this.documentItems = documentItems;
        this.template = template;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentItem> getDocumentItems() {
        return documentItems;
    }

    public void setDocumentItems(List<DocumentItem> documentItems) {
        if (this.documentItems == null) {
            this.documentItems = new ArrayList<>();
        }

        this.documentItems.clear();
        this.documentItems.addAll(documentItems);
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

}
