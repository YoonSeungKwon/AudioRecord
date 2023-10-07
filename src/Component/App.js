import React, {useState} from "react";
import { ReactMic } from "react-mic";
import axios from "axios";

function App() {

  const [recordBlob, setRecordBlob] = useState(null);
  const [record, setRecord] = useState(false);

  const formData = new FormData();

  const onData = (data) =>{
    console.log("real time data is: " + data);
  };

  const onStop = (recordBlob) =>{
    console.log('recordBlob is : ' + recordBlob)
    setRecordBlob(recordBlob);
  };

  const onStart = () =>{
    setRecord(true);
  }

  const onEnd = () =>{
    setRecord(false);
  }

  const sendHandler = () =>{
    if(recordBlob){
      formData.append('audio', recordBlob.blob, 'audio.wave');
      console.log(recordBlob)
      axios.post('http://localhost:8080/api/v1/stt/', formData
      ).then((res)=>{
        console.log('Success' + res.data)
      }).catch((error)=>{
        console.log('Error!' + error)
      })
    }
  }

  return (
    <div>
      <ReactMic
        record={record}
        className="sound-wave"
        visualSetting="frequencyBars"
        onStop={onStop}
        onData={onData}
        strokeColor="#000000"
        backgroundColor="#333333"
        mimeType="audio/wav"
      />
      <br/>
      <div>
        <button onClick={onStart}>녹음 시작</button>
        <br/>
        <button onClick={onEnd}>녹음 종료</button>
        <br/>
        <button onClick={() => setRecordBlob(null)}>다시 녹음</button>
      </div>
      {recordBlob && (
        <audio controls>
          <source src={recordBlob.blobURL} type="audio/wav" />
          Your browser does not support the audio element.
        </audio>
      )}
      <button onClick={sendHandler}>전송</button>
    </div>
  );
}

export default App;
