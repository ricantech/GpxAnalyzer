package rican.task.data.gpxAnalyzer.controller;

import com.google.common.base.Stopwatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rican.task.data.gpxAnalyzer.controller.response.PathRepresentation;
import rican.task.data.gpxAnalyzer.controller.response.PathWithDetailRepresentation;
import rican.task.data.gpxAnalyzer.controller.response.RepresentationConverter;
import rican.task.data.gpxAnalyzer.controller.validator.GpxFile;
import rican.task.data.gpxAnalyzer.service.PathService;
import rican.task.data.gpxAnalyzer.model.Path;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/paths")
@Validated
@Api(value = "Path controller", description = "Basic path operations")
public class PathController {

    private static final Logger log = LoggerFactory.getLogger(PathController.class);

    private final PathService service;
    private final RepresentationConverter converter;

    public PathController(PathService service, RepresentationConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "File uploaded and processed."),
            @ApiResponse(code = 400, message = "File not accepted or cannot be processed - more info in error"),
            @ApiResponse(code = 500, message = "Something went wrong on our side")
    })
    public ResponseEntity<PathWithDetailRepresentation> saveFile(@RequestParam("file") @GpxFile MultipartFile file) {
        log.info("File received {}", file.getOriginalFilename());
        Stopwatch stpwtch = Stopwatch.createStarted();
        Path result = service.process(file);
        log.info("File processed in {} ms.", stpwtch.elapsed(MILLISECONDS));
        return created(linkTo(PathController.class).slash(result.getId()).toUri()).body(converter.convertWithDetail(result));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all stored paths")
    })
    public ResponseEntity<List<PathRepresentation>> getAllPaths() {
        List<Path> paths = service.getAllPaths();
        List<PathRepresentation> pathRepresentations = paths.stream().map(converter::convertWithDetailLink).collect(Collectors.toList());
        return ok(pathRepresentations);
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Detail of path"),
            @ApiResponse(code = 404, message = "Path with give ID not found.")
    })
    public ResponseEntity<PathWithDetailRepresentation> getPathDetails(@PathVariable Long id) {
        Path path = service.getPathDetails(id);
        return ok(converter.convertWithDetail(path));
    }
}