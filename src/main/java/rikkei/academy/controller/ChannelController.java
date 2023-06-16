package rikkei.academy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.ChannelDTO;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Channel;
import rikkei.academy.model.User;
import rikkei.academy.security.userPrincipal.UserDetailService;
import rikkei.academy.service.channelService.IChannelService;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private IChannelService channelService;
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<List<Channel>> findAll() {
        return ResponseEntity.ok(channelService.findAll());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createChannel(@RequestBody Channel channel) {
        User user = userDetailService.getUserPrinciple();
        System.out.println(user);
        if (user.getId() == channel.getOwner().getId()) {
            if (channel.getStatus() == 0) {
                channel.setStatus(1);
                return ResponseEntity.ok(channelService.save(channel));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseMessage.builder()
                        .status("Thất bại")
                        .message("Thêm mới thất bại")
                        .data("")
                        .build()
        );
    }

//    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<?> updateChannel(@RequestBody ChannelDTO channel) {
//        Channel channelUpdate = channelService.findById(channel.getId());
//        if (channelUpdate.getId() == channel.getId()) {
//            if (channel.getName() != null) {
//                channelUpdate.setName(channel.getName());
//            } else if (channel.getDescription() != null) {
//                channelUpdate.setDescription(channel.getDescription());
//            } else if (channel.getStatus() != 0) {
//                channelUpdate.setStatus(channel.getStatus());
//            }
//            return ResponseEntity.ok().body(
//                    ResponseMessage.builder()
//                            .status("OK")
//                            .message("Sửa kênh thành công")
//                            .data(channelService.save(channelUpdate))
//                            .build()
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    ResponseMessage.builder()
//                            .status("Thất bại")
//                            .message("Sửa kênh thất bại")
//                            .data("")
//                            .build()
//            );
//        }
//
//    }

    @DeleteMapping("/deleteChannel/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void deleteChannel(@PathVariable Long id) {
        channelService.delete(id);
    }

    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) {
        Channel channelChangeStatus = channelService.findById(id);
        switch (channelChangeStatus.getStatus()) {
            case 1:
                channelChangeStatus.setStatus(2);
                channelService.save(channelChangeStatus);
                return ResponseEntity.ok("cảnh báo vi lần 1 thành công");
            case 2:
                channelChangeStatus.setStatus(3);
                channelService.save(channelChangeStatus);
                return ResponseEntity.ok("cảnh báo vi lần 2 thành công");
            case 3:
                channelChangeStatus.setStatus(4);
                channelService.save(channelChangeStatus);
                return ResponseEntity.ok("cảnh báo vi lần 3 thành công");
            case 4:
                channelChangeStatus.setStatus(0);
                channelService.save(channelChangeStatus);
                return ResponseEntity.ok("khóa channel thành công");
            default:
                break;
        }
        return ResponseEntity.ok(channelService.save(channelChangeStatus));
    }
}
