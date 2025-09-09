package org.example.study_group_service.models.dto.outcomming;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO<T> {
    private Integer pageSize;
    private Integer pageNumber;
    private Integer elementsTotal;
    private Integer pagesTotal;
    private List<T> content;

    public PageDTO(Page<T> page){
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.pagesTotal = page.getTotalPages();
        this.elementsTotal = page.getNumberOfElements();
        this.content = page.getContent();
    }

    public <R> PageDTO(Page<R> page, List<T> content){
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.pagesTotal = page.getTotalPages();
        this.elementsTotal = page.getNumberOfElements();
        this.content = content;
    }
}