package com.ai.ServiceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ai.entity.Attendance;
import com.ai.entity.Batch;
import com.ai.entity.User;
import com.ai.entity.User.Role;
import com.ai.repository.AttendanceRepository;
import com.ai.service.AttendanceService;


 @SpringBootTest

 public class AttendanceServiceTest {

     @Mock
     AttendanceRepository attendanceRepository;

     @InjectMocks
     AttendanceService attendanceService;

     public Batch batchObj(){
         Batch batch = Batch.builder()
                 .id(1)
                 .name("Batch-1")
                 .startDate(LocalDate.of(2022, 9, 23))
                 .endDate(LocalDate.of(2022, 12, 6))
                 .build();
         return batch;
     }

     public User userObj(){
         User user = User.builder()
                 .loginId("ADM001")
                 .name("Admin User")
                 .password("admin")
                 .role(Role.Admin)
                 .photo("photo.png")
                 .email("phyuthin2004@gmail.com")
                 .isActive(true)
                 .build();
         return user;
     }

    public Attendance attendanceObj(){
        Attendance attendance = Attendance.builder()
        .id(1)
        .status("Present")
        .date(LocalDate.of(2022, 12, 6))
        .batch(batchObj())
        .user(userObj())
        .build();
        return attendance;
    }

    public List<Attendance> attendanceList(){
        List<Attendance> attendanceList = new ArrayList<Attendance>();

        Attendance attendance1 = Attendance.builder()
        .id(1)
        .status("Present")
        .date(LocalDate.of(2022, 12, 6))
        .batch(batchObj())
        .user(userObj())
        .build();

        Attendance attendance2 = Attendance.builder()
        .id(2)
        .status("Absent")
        .date(LocalDate.of(2022, 12, 6))
        .batch(batchObj())
        .user(userObj())
        .build();

        Attendance attendance3 = Attendance.builder()
        .id(3)
        .status("Leave")
        .date(LocalDate.of(2022, 12, 6))
        .batch(batchObj())
        .user(userObj())
        .build();

        attendanceList.add(attendance1);
        attendanceList.add(attendance2);
        attendanceList.add(attendance3);

        return attendanceList;
    }

    @Test
    public void saveTest(){
        Attendance attendance = attendanceObj();
        attendanceService.save(attendance);
        verify(attendanceRepository, times(1)).save(attendance);
    }

    @Test
    public void findAllTest(){
        List<Attendance> attendances = attendanceList();
        Mockito.when(attendanceRepository.findAll()).thenReturn(attendances);
        List<Attendance> attendanceList = attendanceService.findAllAttendance();
        assertEquals(3, attendanceList.size());
        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest(){
        Attendance attendance = attendanceObj();
        Mockito.when(attendanceRepository.findById(1)).thenReturn(attendance);
        Attendance getAttendance = attendanceService.findById(1);
        assertEquals("Present", getAttendance.getStatus());
    }

    @Test
    public void getCountTest(){
        int serviceCount = attendanceService.count();
        int repositoryCount = (int) attendanceRepository.count();
        assertEquals(serviceCount, repositoryCount);
    }

    @Test
    public void findAllAttendanceByBatchIdTest(){
        List<Attendance> attendances = attendanceList();
        Mockito.when(attendanceRepository.findAllAttendanceByBatchId(1)).thenReturn(attendances);
        List<Attendance> attendanceList = attendanceService.findAllAttendanceByBatchId(1);
        assertEquals(3, attendanceList.size());
        verify(attendanceRepository, times(1)).findAllAttendanceByBatchId(1);
    }

    @Test
    public void findByDateTest(){
        Attendance attendance = attendanceObj();
        Mockito.when(attendanceRepository.findByDate(LocalDate.of(2022, 12, 6))).thenReturn(attendance);
        Attendance getAttendance = attendanceService.findByDate(LocalDate.of(2022, 12, 6));
        assertEquals("Present", getAttendance.getStatus());
    }
}
