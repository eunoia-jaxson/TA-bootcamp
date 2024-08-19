import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.HashSet;
import java.util.Set;

// Main 클래스 - 프로그램의 진입점
public class Main {
    public static void main(String[] args) {
        NumberBaseballGame game = new NumberBaseballGame();
        game.start();
    }
}

// NumberBaseballGame 클래스 - 게임의 전반적인 흐름 관리
class NumberBaseballGame {
    private Scanner scanner = new Scanner(System.in);
    private GameLogic gameLogic = new GameLogic();
    private InputHandler inputHandler = new InputHandler(scanner);

    public void start() {
        System.out.println("숫자 야구 게임을 시작합니다.");
        
        while (true) {
            int[] computerNumbers = gameLogic.generateNumbers();

            while (true) {
                int[] userNumbers = inputHandler.getUserInput();

                Result result = gameLogic.calculateResult(computerNumbers, userNumbers);

                if (result.strike == 3) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    break;
                } else if (result.strike == 0 && result.ball == 0) {
                    System.out.println("OUT");
                } else if (result.strike == 0) {
                    System.out.println(result.ball + "B");
                } else if (result.ball == 0) {
                    System.out.println(result.strike + "S");
                } else {
                    System.out.println(result.ball + "B " + result.strike + "S");
                }
            }

            System.out.println("게임을 새로 시작하려면 Y, 종료하려면 N을 입력하세요.");
            String start = scanner.next();
            if (start.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}

// GameLogic 클래스 - 게임 로직 처리
class GameLogic {

    // 컴퓨터 숫자 생성
    public int[] generateNumbers() {
        int[] computer = new int[3];
        for (int i = 0; i < computer.length; i++) {
            computer[i] = (int) (Math.random() * 9) + 1;
            for (int j = 0; j < i; j++) {
                if (computer[i] == computer[j]) {
                    i--;
                    break;
                }
            }
        }
        return computer;
    }

    // 결과 계산
    public Result calculateResult(int[] computer, int[] user) {
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
        return new Result(strike, ball);
    }
}

// Result 클래스 - 스트라이크와 볼의 결과를 담는 클래스
class Result {
    int strike;
    int ball;

    public Result(int strike, int ball) {
        this.strike = strike;
        this.ball = ball;
    }
}

// InputHandler 클래스 - 사용자 입력 처리 및 유효성 검사
class InputHandler {
    private Scanner scanner;
    private static int COUNT = 0;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    // 사용자 입력 받기
    public int[] getUserInput() {
        int number = 0;
        while (true) {
            System.out.print("숫자를 입력해주세요: ");
            try {
                number = scanner.nextInt();
                if (isValidInput(number)) {
                    int[] userInputArray = convertToArray(number);
                    if (hasDuplicateDigits(userInputArray)) {
                        throw new IllegalArgumentException("숫자에 중복이 있습니다. 서로 다른 세 자리 숫자를 입력해주세요.");
                    }
                    COUNT = 0;
                    return userInputArray;
                } else {
                    System.out.println("유효하지 않은 입력입니다. 세 자리 숫자를 입력해주세요.");
                    COUNT += 1;
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요.");
                COUNT += 1;
                scanner.next();  // 잘못된 입력을 버퍼에서 제거
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                COUNT += 1;
            } finally {
                if (COUNT == 3) {
                    System.out.println("연속 3번 잘못된 입력을 하셨습니다. 게임을 종료합니다.");
                    System.exit(0);
                }
            }
        }
    }

    // 숫자 유효성 검사
    private boolean isValidInput(int number) {
        return number >= 100 && number <= 999;
    }

    // 숫자를 배열로 변환
    private int[] convertToArray(int number) {
        int[] user = new int[3];
        for (int i = user.length - 1; i >= 0; i--) {
            user[i] = number % 10;
            number /= 10;
        }
        return user;
    }

    // 숫자 중복 확인
    private boolean hasDuplicateDigits(int[] array) {
        Set<Integer> digitSet = new HashSet<>();
        for (int digit : array) {
            if (!digitSet.add(digit)) {
                return true;  // 중복이 발견되면 true 반환
            }
        }
        return false;  // 중복이 없으면 false 반환
    }
}
