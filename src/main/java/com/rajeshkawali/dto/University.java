package com.rajeshkawali.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rajesh_Kawali
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class University {

    @JsonProperty("name")
    private String name;

    @JsonProperty("alpha_two_code")
    private String alphaTwoCode;

    @JsonProperty("state-province")
    private String stateProvince;

    @JsonProperty("country")
    private String country;

    @JsonProperty("domains")
    private List<String> domains;

    @JsonProperty("web_pages")
    private List<String> webPages;
}