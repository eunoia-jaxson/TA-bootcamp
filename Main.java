import java.util.Scanner;

// 숫자 야구 게임
public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String start = "Y";

    System.out.println("숫자 야구 게임을 시작합니다.");
    
    while (start == "Y") {

      // 서로 다른 세 자리 숫자 생성
      int[] computer = new int[3];
      for (int i = 0; i < computer.length; i++) {
        computer[i] = (int)(Math.random() * 9) + 1;
        for (int j = 0; j < i; j++) {
          if (computer[i] == computer[j]) {
            i--;
            break;
          }
        }
      }


      // 사용자로부터 세 자리 숫자 입력받기
      System.out.println("숫자를 입력해주세요: ");
      int number = scanner.nextInt();
      int[] user = new int[3];

      // 사용자가 입력한 숫자를 배열에 저장
      for (int i = user.length - 1; i >= 0; i--) {
        user[i] = number % 10;
        number /= 10;
      }

      System.out.println("사용자가 입력한 숫자: " + user[0] + user[1] + user[2]);
      
      System.out.println("게임을 새로 시작하려면 Y, 종료하려면 N을 입력하세요.");
      start = scanner.next();
    }
  }
}