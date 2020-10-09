package com.sibtain.supercms.models;

import com.sibtain.supercms.models.data.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page,Integer> {

    Page findBySlug(String slug);

}
