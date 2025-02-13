package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.NotFound;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule){
        return (schedule!=null)? scheduleRepository.save(schedule):null;
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetId (Long petId) throws NotFound{
        if(petId!=null) {
            Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByPetId(petId);
            return schedules.orElseThrow(
                    () -> new NotFound(
                            String.format("The schedule of Pet Id: %s cannot be found", petId)
                    ));
        } return null;
    }

    public List<Schedule> findScheduleByEmployeeId (Long employeeId) throws NotFound{
        if(employeeId!=null) {
            Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByEmployeeId(employeeId);
            return schedules.orElseThrow(
                    () -> new NotFound(
                            String.format("The schedule of Employee Id: %s cannot be found", employeeId)
                    ));
        } else return null;
    }

    public List<Schedule> findScheduleByCustomerId (Long customerId) throws NotFound{
        if(customerId!=null) {
            Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByCustomerId(customerId);
            return schedules.orElseThrow(
                    () -> new NotFound(
                            String.format("The schedule of Customer Id: %s cannot be found", customerId)
                    ));
        } else return null;
    }
}
