package rican.task.data.gpxAnalyzer.jenetics.calculator;

import io.jenetics.jpx.geom.Geoid;
import org.springframework.stereotype.Component;

@Component
public class WGS84Calculator extends AbstractGeodeticCalculator {

    public WGS84Calculator() {
        super(Geoid.WGS84);
    }
}
