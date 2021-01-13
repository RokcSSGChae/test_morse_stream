package com.morse.streaming.repository;

import com.morse.streaming.model.Room;
import com.morse.streaming.model.UserSession;
import org.kurento.client.KurentoClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
