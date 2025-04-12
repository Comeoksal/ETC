import java.util.*;
import java.io.*;
class Process{
    private int Number; //프로세스 번호
    private int S;      //프로세스의 크기값(Byte)
    private int Page_table[]; //프로세스의 페이지 테이블(크기 S에 따라서 메모리 할당)
    private int Table_size;   //테이블 사이즈(할당 받고 있는 프레임 수)
    //프로세스 생성자(바이트 크기, 프로세스 번호)
    public Process(int _ByteSize, int _number){ 
        S = _ByteSize;
        Number = _number;
    }
    //프로세스 변수값 반환 메소드들
    public int get_S(){
        return S;
    }
    public int get_Number(){
        return Number;
    }
    public int get_Tablesize(){
        return Table_size;
    }
    //프로세스의 크기(S)에 따라 테이블 사이즈를 구하여 할당 후 배열 크기 할당 메소드
    public void allocate_Pagetable(int _tablesize){
        Table_size = _tablesize;
        Page_table = new int[_tablesize];
    }
    //페이지 테이블의 페이지 번호에 프레임 번호 입력 메소드
    public void set_Pagetable(int page_num, int frame_num){
        Page_table[page_num] = frame_num;
    }
    //페이지 번호로 프레임 번호 반환 받는 메소드
    public int get_Pagetable(int page_num){
        return Page_table[page_num];
    }
}
public class Main {
    static final int Memory_Size = 1024; //메인 메모리 크기
    static final int Page_Size = 16;     //페이지(프레임) 크기
    static final int Frame_Size = Memory_Size/Page_Size; //프레임 사이즈(프레임 갯수)
    static final int Min_S_Size = 20; //프로세스 크기 최소값
    static final int Max_S_Size = 100;//프로세스 크기 최대값
   
    static int Frame_table[] = new int[Frame_Size]; //프레임 테이블[프레임 갯수]
    static int p_number = 1; //프로세스 수(번호)를 카운팅 할 변수(생성마다 1씩 증가)
    static Queue<Process> Process_List = new LinkedList<>(); //프레임 할당을 받은 프로세스를 담아둘 큐
    static void createProcess(){
        //프로세스 S의 크기 결정 20<=S<=100
        int S = (int)(Math.random() * (Max_S_Size - Min_S_Size + 1)) + Min_S_Size;
        //프로세스 생성(프로세스 크기, 번호) 후 p_number++
        Process process = new Process(S, p_number);
        p_number++;
        //페이지 테이블 배열의 크기 구하고 할당
        int table_size = (int)(S/Page_Size); //프로세스 크기(20~100바이트)/페이지 크기(16바이트)
        int rest_byte = S%Page_Size; //페이지 크기만큼 나누고 남는 나머지 바이트
        boolean has_RestByte=false; //나머지 바이트가 있을 수 있고, 없을 수 있음(나머지가 0으로 떨어지는 크기)
        if(rest_byte!=0){ //남는 바이트가 있다면
            has_RestByte = true; //변수 = true
            //테이블 사이즈 늘려주기
            table_size++;
        }
        process.allocate_Pagetable(table_size);
        // Frame_table의 인덱스(프레임 번호) 하나씩 랜덤 함수로 할당. 이때 해당 인덱스의 내용물 값이 0(빈 프레임) 일 때만
        // 할당하고 아니면 (랜덤 함수)로 다른 인덱스로 다시 할당(continue) 반복(while문)
        for(int i=0; i<table_size; i++){
            while(true){
                int index = (int)(Math.random() * Frame_Size);
                if(Frame_table[index]==0){
                    //마지막 프레임 할당하려 할 때, 남는 바이트가 있으면 그 크기 입력(프레임은 전체 다 할당)
                    if(i==(table_size-1)&&has_RestByte){
                        Frame_table[index] = rest_byte;
                        process.set_Pagetable(i, index);
                        break;
                    }
                    //프레임 할당, 프로세스의 페이지 테이블에 기입
                    Frame_table[index] = Page_Size;
                    process.set_Pagetable(i, index);
                    break;
                }
                else{
                    continue;
                }
            }
        }
        //프레임 할당, 페이지 테이블 기입이 완료된 프로세스를 큐에 삽입
        Process_List.offer(process);
    }
    static void terminateProcess(){
        //가장 먼저 프레임 할당 받은 프로세스 큐에서 꺼내기(종료)
        Process tmp = Process_List.poll();
        //해당 프로세스의 페이지 테이블을 읽으며, 프레임 테이블에서 사용 중인 프레임 메모리 해제
        for(int i=0; i<tmp.get_Tablesize(); i++){
            int page_num = tmp.get_Pagetable(i);
            Frame_table[page_num] = 0;
        }
    }
    static void print_Tables(){
        System.out.print("FrameTable(FrameNumber[Used Byte]) : [ ");
        //프레임 테이블 배열의 인덱스를 하나씩 방문하여, 해당 내용값이 0(빈 프레임)이 아닐 시 출력
        for(int i=0; i<Frame_Size; i++){
            if(Frame_table[i]!=0){ //사용 중인 프레임 찾는 조건문
                System.out.print(i + "["+Frame_table[i] +"]" + " ");
            }
        }
        System.out.println("]");
        //프레임 할당 받은 프로세스 수만큼 출력
        for(int i=0; i<Process_List.size(); i++){
            Process tmp = Process_List.poll(); //프로세스 꺼내기
            System.out.print("Process["+tmp.get_Number()+"]("+tmp.get_S()+"Byte)PageTable => ");
            //꺼낸 프로세스의 페이지 테이블 읽고 출력
            for(int j=0; j<tmp.get_Tablesize(); j++){
                System.out.print("["+j+":"+tmp.get_Pagetable(j)+"] ");
            }
            System.out.println();
            Process_List.offer(tmp);//꺼낸 프로세스 다시 큐에 삽입
        }
    }

    public static void main(String[] args) throws IOException {
        //각 프로세스 생성, 종료 시 마다 테이블들의 상태를 보여줌
        //2개 생성 1개 종료, 1개 생성 2개 종료를 반복문으로 7번 반복 하여 21개의 프로세스 처리
        for(int i=0; i<7; i++){
            //2개의 프로세스 생성
            System.out.println("<==========Create Two Process==========>");
            createProcess();
            createProcess();
            print_Tables();
            //1개의 프로세스 종료
            System.out.println("<==========Terminate One Process==========>");
            terminateProcess();
            print_Tables();
            //1개의 프로세스 생성
            System.out.println("<==========Create One Process==========>");
            createProcess();
            print_Tables();
            //2개의 프로세스 종료
            System.out.println("<==========Terminate Two Process==========>");
            terminateProcess();
            terminateProcess();
            print_Tables();
        }
    }
}
