package com.nexstudio.msscbeerservice.web.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BeerPagedList extends PageImpl<BeerDTO> {

    public BeerPagedList(List<BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
        //TODO Auto-generated constructor stub
    }

    public BeerPagedList(List<BeerDTO> content) {
        super(content);
        //TODO Auto-generated constructor stub
    }
    
}
