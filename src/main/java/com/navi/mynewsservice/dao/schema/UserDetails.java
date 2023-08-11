package com.navi.mynewsservice.dao.schema;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="client")
public class UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String email;
        private String country;
        private String category;

        @ManyToMany
        private List<SourceDetails> sources;

        // Default constructor
        public UserDetails() {
                // Empty constructor required by Hibernate
        }

        public UserDetails(String email, String country, String category, List<SourceDetails> sources) {
                this.email = email;
                this.country = country;
                this.category = category;
                this.sources = sources;
        }
}
