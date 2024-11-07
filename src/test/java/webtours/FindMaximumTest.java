package webtours;

import io.gatling.javaapi.core.Simulation;
import jdk.jfr.Description;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.incrementUsersPerSec;

@Description("В данном тесте посекундно добавляем пользователей в поисках максимума, при котором ситема может работать")
public class FindMaximumTest extends Simulation {
    {
        setUp(
                CommonScenario.byTicketsScenario().injectOpen(
                        incrementUsersPerSec(1) // Сколько добавляем на каждом шаге
                                .times(10) // Увеличиваем нагрузку в течении 10 шагов
                                .eachLevelLasting(Duration.ofSeconds(5)) // Продолжительность каждого уровня нагрузки (10 сек минуты)
                                .separatedByRampsLasting(Duration.ofSeconds(10)) // Время между уровнями
                                .startingFrom(0) // Начинаем с нуля
                ).protocols(Base.httpProtocolCustom)).assertions(
        );
    }
    //по результатам тестового прогона - 300 одновременно работающих пользователей
}
