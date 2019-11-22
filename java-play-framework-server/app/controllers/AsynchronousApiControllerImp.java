package controllers;

import apimodels.Request;
import apimodels.TransformerQuery;

import play.mvc.Http;
import sharpener.AssynchronousRequest;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-08T23:25:48.604Z")

public class AsynchronousApiControllerImp implements AsynchronousApiControllerImpInterface {
    @Override
    public Request statusRequestIdGet(String requestId) throws Exception {
    	return AssynchronousRequest.status(requestId);
    }

    @Override
    public Request submitPost(TransformerQuery query) throws Exception {
        return AssynchronousRequest.submit(query);
    }

}
