package webtours;

import io.gatling.javaapi.core.Simulation;
import jdk.jfr.Description;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

@Description("В данном тесте повышаем нагрузку до максимума (330 юзеров) в течении 20 минут добавляя по 10%")
public class StepLoadTest extends Simulation {
    {
        setUp(
                CommonScenario.byTicketsScenario().injectClosed(
                        incrementConcurrentUsers(33) // На каждом уровне добавляем 33 пользователя
                                .times(10) // Всего 10 уровней (0% до 100% нагрузки)
                                .eachLevelLasting(Duration.ofMinutes(2)) // Длительность каждого уровня — 2 минуты
                                .separatedByRampsLasting(Duration.ofSeconds(10)) // Плавный переход между уровнями
                                .startingFrom(0) // Начинаем с 0 пользователей
                ).protocols(Base.httpProtocolCustom)
        );
    }
}
