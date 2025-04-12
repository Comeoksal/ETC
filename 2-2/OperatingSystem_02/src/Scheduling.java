import java.util.*;

class Process {
    private String process_id;
    private int arrive_time, burst_time;
    private int totalB_time;

    public Process(String _id, int _atime, int _btime) {
        this.process_id = _id;
        this.arrive_time = _atime;
        this.burst_time = _btime;
        totalB_time = _btime;
    }
    public String getProcessId() {
        return process_id;
    }

    public int getArriveTime() {
        return arrive_time;
    }

    public int getBurstTime() {
        return burst_time;
    }
    public void useBurstTime(int _setting){
        burst_time -= _setting;
    }
    public int watingTime(int _time){
        return _time - arrive_time - totalB_time;
    }
}

public class Scheduling {
    public static void main(String[] args) throws Exception {
        PriorityQueue<Process> Process_zip = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process a1, Process a2){
                return Integer.compare(a1.getArriveTime(), a2.getArriveTime());
            }
        });
        Process_zip.add(new Process("P1", 0, 10)); 
        Process_zip.add(new Process("P2", 1, 7)); 
        Process_zip.add(new Process("P3", 2, 8)); 
        Process_zip.add(new Process("P4", 3, 6)); 
        Process_zip.add(new Process("P5", 4, 12));
        Process_zip.add(new Process("P6", 5, 11)); 
        Process_zip.add(new Process("P7", 6, 3)); 
        Process_zip.add(new Process("P8", 7, 9)); 
        Process_zip.add(new Process("P9", 8, 2)); 
        Process_zip.add(new Process("P10", 9, 1)); 
        //SJFS(Process_zip);
        //RRS(Process_zip, 4);
        RRS(Process_zip, 8);
    }
    public static void SJFS(PriorityQueue<Process> _processzip){
        PriorityQueue<Process> Ready_Queue = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process a1, Process a2){
                return Integer.compare(a1.getBurstTime(), a2.getBurstTime());
            }
        });
        PriorityQueue<Process> process_zip = _processzip;
        StringBuilder sb = new StringBuilder();

        int time = 0;
        int zip_size;
        double totalW_time = 0;
        int totalP_size = process_zip.size();
        boolean check_PriorityProcess;
        Process execute_Process;
        Process test_process;

        Ready_Queue.offer(process_zip.poll());
        print_ReadyQueue(Ready_Queue);
        sb.append("SJF Gantt Chart : [  ");
        while(!Ready_Queue.isEmpty()){
            int now_time = time;
            check_PriorityProcess = false;
            execute_Process = Ready_Queue.poll();
            while(true){
                execute_Process.useBurstTime(1);
                time++;
                zip_size = process_zip.size();
                while(zip_size-->0){
                    test_process = process_zip.poll();
                    if(test_process.getArriveTime()==time){
                        if(test_process.getBurstTime()<execute_Process.getBurstTime()){
                            check_PriorityProcess = true;
                        }
                        Ready_Queue.offer(test_process);
                    }
                    else{
                        process_zip.offer(test_process);
                    }
                }
                if(check_PriorityProcess){
                    if(execute_Process.getBurstTime()>0){
                        Ready_Queue.offer(execute_Process);
                    }
                    sb.append(execute_Process.getProcessId()+"("+now_time+"~"+time+")  " );
                    System.out.println(sb.toString()+"\n");
                    print_ReadyQueue(Ready_Queue);
                    break;
                }
                else if(execute_Process.getBurstTime()==0){
                    sb.append(execute_Process.getProcessId()+"("+now_time+"~"+time+")  " );
                    System.out.println(sb.toString()+"\n");
                    totalW_time+=execute_Process.watingTime(time);
                    print_ReadyQueue(Ready_Queue);
                    break;
                }
                else{
                    continue;
                }
            }
        }
        sb.append("]").append("\n");
        System.out.print(sb.toString());
        System.out.printf("Average Wating time : %.1f", totalW_time/totalP_size);
    }
    public static void RRS(PriorityQueue<Process> _processzip, int time_assignment){
        Queue<Process> Ready_Queue = new LinkedList<>();
        PriorityQueue<Process> process_zip = _processzip;
        StringBuilder sb = new StringBuilder();

        int time=0;
        int zip_size;
        double totalW_time = 0;
        int totalP_size = process_zip.size();
        Process execute_Process;
        Process test_process;

        Ready_Queue.offer(process_zip.poll());
        print_ReadyQueue(Ready_Queue);
        sb.append("RRS Gantt Chart : [  ");
        while(!Ready_Queue.isEmpty()){
            int now_time = time;
            execute_Process = Ready_Queue.poll();
            while(true){
                execute_Process.useBurstTime(1);
                time++;
                zip_size = process_zip.size();
                while(zip_size-->0){
                    test_process = process_zip.poll();
                    if(test_process.getArriveTime()==time){
                        Ready_Queue.offer(test_process);
                    }
                    else{
                        process_zip.offer(test_process);
                    }
                }
                if(time-now_time==time_assignment){
                    if(execute_Process.getBurstTime()>0){
                        Ready_Queue.offer(execute_Process);
                        sb.append(execute_Process.getProcessId()+"("+now_time+"~"+time+")  " );
                        System.out.println(sb.toString()+"\n");
                        print_ReadyQueue(Ready_Queue);
                    }
                    else{
                        sb.append(execute_Process.getProcessId()+"("+now_time+"~"+time+")  " );
                        System.out.println(sb.toString()+"\n");
                        totalW_time+=execute_Process.watingTime(time);
                        print_ReadyQueue(Ready_Queue);
                    }
                    break;
                }
                else if(execute_Process.getBurstTime()==0){
                    sb.append(execute_Process.getProcessId()+"("+now_time+"~"+time+")  " );
                    System.out.println(sb.toString()+"\n");
                    totalW_time+=execute_Process.watingTime(time);
                    print_ReadyQueue(Ready_Queue);
                    break;
                }
                else{
                    continue;
                }
            }
        }
        sb.append("]" +"\n");
        System.out.print(sb.toString());
        System.out.printf("Average Wating time : %.1f", totalW_time/totalP_size);
    }
    public static void print_ReadyQueue(Queue<Process> _ReadyQueue){
        StringBuilder sb = new StringBuilder();
        sb.append("Now Ready Queue = [  ");
        for(Process test_process : _ReadyQueue){
            sb.append(test_process.getProcessId()+"  ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}
