package webtours;

import io.gatling.javaapi.core.Simulation;
import jdk.jfr.Description;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

@Description("В данном случае, создаем сценарий, который будет поддерживать нагрузку в 20 RPS в течение часа ")
public class ReliabilityTest extends Simulation {
    {
        setUp(
                CommonScenario.byTicketsScenario().injectClosed(constantConcurrentUsers(200).during(Duration.ofMinutes(60)))
        )
                .throttle(
                        reachRps(20).in(10),            // Поднимаем значение RPS == 20 за 10 секунд
                        holdFor(Duration.ofMinutes(60)),   // Держать на уровне 20 RPS в течение 60 минут
                        jumpToRps(10),                   // После этого понизить до 10 RPS в секунду
                        holdFor(Duration.ofMinutes(2))     // Держать 10 RPS в течение 2 минуты
                ).protocols(Base.httpProtocolCustom);
    }
}