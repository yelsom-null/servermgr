package com.array.servermanager.repo;

import com.array.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;



    public interface ServerRepo extends JpaRepository<Server, Long>{ // Object & id type
        //Custom method
        Server findByIpAddress(String ipAddress);

    }

