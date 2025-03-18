import { useState, useRef, useEffect } from 'react';
import { IconButton } from 'rsuite';
import { MdPause, MdPlayArrow } from 'react-icons/md';
import { Col, Form, Row } from 'react-bootstrap';

const AudioPlayer = ({ songId, songTitle, album }: { songId: string, songTitle: string, album: string }) => {
    const audioRef = useRef<HTMLAudioElement | null>(null);
    const [duration, setDuration] = useState<number>(0);
    const [currentTime, setCurrentTime] = useState<number>(0);
    const [title, setTitle] = useState<string>('');
    const [isReady, setIsReady] = useState(false);
    const [isPlaying, setIsPlaying] = useState(false);

    useEffect(() => {
        audioRef.current?.pause();

        const timeout = setTimeout(() => {
            audioRef.current?.load();
        }, 500);

        return () => {
            clearTimeout(timeout);
        };
    }, [songId]);

    return (
        <>
            <div className="d-inline-block h-100 w-100 border border-3 border-success rounded">
                <audio ref={audioRef} preload="metadata"
                    onDurationChange={(e) => setDuration((parseFloat(e.currentTarget.duration) / 60).toFixed(2))}
                    onLoadedMetadata={(e) => setTitle(e.currentTarget.title)}
                    onCanPlay={() => setIsReady(true)}
                    onPlaying={() => setIsPlaying(true)}
                    onPause={() => setIsPlaying(false)}
                    onTimeUpdate={(e) => { setCurrentTime((parseFloat(e.currentTarget.currentTime) / 60).toFixed(2)) }}>
                    <source type="audio/mpeg"
                        src={`http://localhost:8080/medialib/api/song/${songId}`}
                    />
                </audio>
                <Row className='d-flex flex-row'>
                    <Col className='d-inline-block w-50 text-truncate'>
                        <span className="fw-bold">{title || songTitle}</span>
                    </Col>
                    <Col className='d-inline-block w-50 text-truncate'>
                        <span className="fw-bold">{album}</span>
                    </Col>
                </Row>
                <div className='d-flex flex-row'>
                    <span className="mx-auto fw-bold" >{currentTime} / {duration}</span>
                </div>
                <div className='px-4'>
                    <Form.Range min={0} max={duration} value={currentTime} onChange={(e) => {
                        setCurrentTime(e.currentTarget.valueAsNumber)
                        console.log('buffered : ', audioRef.current?.buffered.length, " : ", audioRef.current?.buffered.start(0), " - ", audioRef.current?.buffered.end(0))
                        console.log('currentTime : ', audioRef.current?.currentTime)
                    }} />
                </div>
                <div className='d-flex flex-row'>
                    {!isPlaying &&
                        <IconButton className='mx-auto' size='sm' circle icon={<MdPlayArrow />} onClick={() => { audioRef.current?.play(); setIsPlaying(true) }} />
                    }
                    {isPlaying &&
                        <IconButton className='mx-auto' size='sm' circle icon={<MdPause />} onClick={() => { audioRef.current?.pause(); setIsPlaying(false) }} />
                    }
                </div>
            </div >
        </>

    )
}

export default AudioPlayer