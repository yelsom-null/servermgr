package com.array.servermanager.service.implementation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.array.servermanager.model.Server;
import com.array.servermanager.repo.ServerRepo;
import com.array.servermanager.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.array.servermanager.enumeration.Status.SERVER_DOWN;
import static com.array.servermanager.enumeration.Status.SERVER_UP;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.data.jpa.domain.JpaSort.of;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;


    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(server.getImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10800) ? SERVER_UP : SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }
    private String setServerImageUrl(){
        String[] imageName = {"server1.png","server2.png","server3.png","server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imageName[new Random().nextInt(4)]).toUriString();
    }


}
