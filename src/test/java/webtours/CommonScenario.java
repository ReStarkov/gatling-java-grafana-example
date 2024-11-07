package webtours;

import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

public class CommonScenario  {
    public static ScenarioBuilder byTicketsScenario() {
        return scenario("webtours testing homework")
                .group("open main page").on(
                        exec(Actions.getMain)
                                .exec(Actions.toNavigate)
                )
                .group("login").on(
                        exec(Actions.logInStep1)
                                .exec(Actions.logInStep2)
                                .exec(Actions.logInStep3)
                )
                .group("search").on(
                        exec(Actions.goToSearch)
                                .exec(Actions.getFlightsMenu)
                                .exec(Actions.goToReservation)
                )
                .group("reservation").on(
                        exec(Actions.createReservation)
                )
                .exec(Actions.setFlight)
                .exec(Actions.setPayments)
                .group("back to main").on(
                        exec(Actions.logInStep2)
                                .exec(Actions.logInStep3)
                );
    }
}
