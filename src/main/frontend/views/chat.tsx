import {TextField,Button} from "@vaadin/react-components";
import {useState} from "react";
import {ChatAiService} from "Frontend/generated/endpoints";

export default function chat(){
    const [question,setQuestion] = useState<string>("");
    const [response,setResponse] = useState<string>("");

    async function send(){
        ChatAiService.ragChat(question).then(resp=>{
            setResponse(resp);
        });
    }
    return(
        <div className="p-m">
            <h3>Chat Bot</h3>
            <div>
                <TextField style={{width:'88%'}} onChange={e=>setQuestion(e.target.value)}/>
                <Button theme="primary" onClick={send}>Send</Button>
            </div>
        </div>
    );
}