import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const MusicPlayer = () => {
    const { id } = useParams();
    // const context = new AudioContext();
    // const [bufferSource, setBufferSource] = useState<AudioBufferSourceNode | null>(null);
    const [audio, setAudio] = useState<Audio | null>(null);

    useEffect(() => {
        setAudio(new Audio(`http://localhost:8080/medialib/api/song/${id}`));
    }, []);

    // useEffect(() => {
    //     // axios.get('http://localhost:8080/medialib/api/song/196e0142-7d8d-4b96-9956-6857722815fc', { responseType: 'arraybuffer' })
    //     //     .then(function (response) {
    //     //         const arrayBuffer = response.data;
    //     //         context.decodeAudioData(arrayBuffer).then(audioBuffer => {
    //     //             console.log("AudioBuffer : " + audioBuffer);
    //     //             const source = context.createBufferSource();
    //     //             source.buffer = audioBuffer;
    //     //             setBufferSource(source);
    //     //         });
    //     //     });
    //     // console.log("Useeffect : " + id);
    //     fetch("http://localhost:8080/medialib/api/song/196e0142-7d8d-4b96-9956-6857722815fc").then(response => {
    //         response.arrayBuffer().then(arrayBuffer => {
    //             context.decodeAudioData(arrayBuffer).then(audioBuffer => {
    //                 console.log("AudioBuffer : " + audioBuffer);
    //                 const source = context.createBufferSource();
    //                 source.buffer = audioBuffer;
    //                 setBufferSource(source);
    //             });
    //         });
    //     });
    // }, []);

    return (
        <div>
            <button
                onClick={() => {
                    audio.play();
                }}
            >
                Play
            </button>
            <button
                onClick={() => {
                    audio.pause();
                }}
            >
                Pause
            </button>
        </div>
    )
}

export default MusicPlayer