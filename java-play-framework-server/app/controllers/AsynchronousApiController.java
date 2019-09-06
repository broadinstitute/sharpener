package controllers;

import apimodels.Request;
import apimodels.TransformerQuery;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import java.io.File;
import swagger.SwaggerUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.validation.constraints.*;
import play.Configuration;

import swagger.SwaggerUtils.ApiAction;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-09-06T15:15:15.958Z")

public class AsynchronousApiController extends Controller {

    private final AsynchronousApiControllerImpInterface imp;
    private final ObjectMapper mapper;
    private final Configuration configuration;

    @Inject
    private AsynchronousApiController(Configuration configuration, AsynchronousApiControllerImpInterface imp) {
        this.imp = imp;
        mapper = new ObjectMapper();
        this.configuration = configuration;
    }


    @ApiAction
    public Result statusRequestIdGet(String requestId) throws Exception {
        Request obj = imp.statusRequestIdGet(requestId);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result submitPost() throws Exception {
        JsonNode nodequery = request().body().asJson();
        TransformerQuery query;
        if (nodequery != null) {
            query = mapper.readValue(nodequery.toString(), TransformerQuery.class);
            if (configuration.getBoolean("useInputBeanValidation")) {
                SwaggerUtils.validate(query);
            }
        } else {
            throw new IllegalArgumentException("'query' parameter is required");
        }
        Request obj = imp.submitPost(query);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }
}
