import com.shiy.kafka.KafkaOffsetValidate;
import org.junit.Test;

public class KafkaOffsetValidateTest {

    @Test
    public void testGetPosition(){
        KafkaOffsetValidate kafkaOffsetValidate = new KafkaOffsetValidate();
        kafkaOffsetValidate.currentOffset();
    }

}
