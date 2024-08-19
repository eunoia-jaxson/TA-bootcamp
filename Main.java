import java.util.Scanner;

// 숫자 야구 게임
public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("숫자 야구 게임을 시작합니다.");
    
    while (true) {
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
      System.out.println("컴퓨터가 숫자를 생성했습니다: " + computer[0] + computer[1] + computer[2]);

      while (true) {
        // 사용자로부터 세 자리 숫자 입력받기
        System.out.print("숫자를 입력해주세요: ");
        int number = scanner.nextInt();
        int[] user = new int[3];

        // 사용자가 입력한 숫자를 배열에 저장
        for (int i = user.length - 1; i >= 0; i--) {
          user[i] = number % 10;
          number /= 10;
        }

        // 스트라이크, 볼 판별
        int strike = 0;
        int ball = 0;
        for (int i = 0; i < computer.length; i++) {
          for (int j = 0; j < user.length; j++) {
            if (computer[i] == user[j]) {
              if (i == j) {
                strike++;
              } else {
                ball++;
              }
            }
          }
        }

        // 결과 출력
        if (strike == 3) {
          System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
          break;
        } else if (strike == 0 && ball == 0) {
          System.out.println("OUT");
        } else if (strike == 0) {
          System.out.println(ball + "B");
        } else if (ball == 0) {
          System.out.println(strike + "S");
        } else {
          System.out.println(ball + "B " + strike + "S");
        }
      }
      
      System.out.println("게임을 새로 시작하려면 Y, 종료하려면 N을 입력하세요.");
      String start = scanner.next();
      if (start.equals("N")) {
        break;
      }
    }
  }
}