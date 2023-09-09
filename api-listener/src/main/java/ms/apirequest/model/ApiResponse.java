package ms.apirequest.model;

import lombok.Data;
import java.util.List;

@Data
public class ApiResponse {

    private List<Anime> data;

    private String status;

    private String type;
}
