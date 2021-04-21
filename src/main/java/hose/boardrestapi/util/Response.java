package hose.boardrestapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private String message;
    private T data;

//    public ResponseEntity<Response<T>> success(T data){
//        this.message = "message";
//        this.data = data;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//
//        return new ResponseEntity<>((Response<T>) this.data, headers, HttpStatus.OK);
//    }
}
