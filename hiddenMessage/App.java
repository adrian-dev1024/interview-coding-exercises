
import java.util.HashSet;
import java.util.Set;

/**
 * Created by adrian on 3/5/17.
 */
public class App {

    static final String usage = "hidden_message   <morse-code-message>   <morse-code-segment-1>   <morse-code-segment-2>";



    public static void removeSegment(StringBuilder message, StringBuilder segment, String result, Set<String> modifedMessages){
        if(message.length() >= segment.length()) {
            if (message.length() == 0 && segment.length() == 0) {
                modifedMessages.add(result);
            }else if(segment.length() == 0){
                modifedMessages.add(result + message.toString());
            }else{
                if(message.charAt(0) != segment.charAt(0)){
                    removeSegment(new StringBuilder(message.substring(1)), segment,
                            result + message.charAt(0), modifedMessages);
                }else{
                    removeSegment(new StringBuilder(message.substring(1)),new StringBuilder(segment.substring(1)),
                            result, modifedMessages);
                    // skip removing the char
                    removeSegment(new StringBuilder(message.substring(1)), segment,
                            result + message.charAt(0), modifedMessages);
                }

            }
        }
    }

    public static Set<String> hiddenMessagePart1(String message, String segment){
        Set<String> modifiedMessages = new HashSet<>();
        removeSegment(new StringBuilder(message), new StringBuilder(segment), "", modifiedMessages);
        return modifiedMessages;
    }

    public static Set<String> hiddenMessagePart2(Set<String> messages, String segment){
        Set<String> modifiedMessages = new HashSet<>();
        messages.stream().forEach((s -> removeSegment(new StringBuilder(s), new StringBuilder(segment), "", modifiedMessages)));
        return modifiedMessages;
    }

    public static void main(String[] args) {

        if(args.length == 3){

            //part 1
            Set<String> modifedMessages1 = hiddenMessagePart1(args[0], args[1]);

            //part 2
            Set<String> modifedMessages2 = hiddenMessagePart2(modifedMessages1, args[2]);
            System.out.println("Answer: " + modifedMessages2.size());
        }else{
            System.out.println("Incorrect usage");
            System.out.println(usage);
            System.exit(1);
        }


    }


}
