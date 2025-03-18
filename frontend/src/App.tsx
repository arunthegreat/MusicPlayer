import { useEffect, useState } from 'react';
import './App.css'
import 'rsuite/IconButton/styles/index.css';
import { MetaData } from './MetaData';
import axios from 'axios';
import { Col, ListGroup, Row } from 'react-bootstrap';
import AudioPlayer from './AudioPlayer';
import { IconButton } from 'rsuite';
import { MdPlayArrow } from 'react-icons/md';
import Buffer from 'buffer';

function App() {
  const [posts, setPosts] = useState<MetaData[]>([]);
  const [selectedSong, setSelectedSong] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [album, setAlbum] = useState<string>('');
  const [imageData, setImageData] = useState<string>('');

  var num = 0;

  useEffect(() => {
    axios.get('http://localhost:8080/medialib/api/findall')
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  function downloadImage(id: string) {
    axios.get('http://localhost:8080/medialib/api/image/' + id)
      .then(response => {
        // console.log(response);
        // console.log(Buffer.btoa(response.data));
        setImageData(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }

  return (
    <Row className='justify-content-center'>
      <Col className='col-10'>
        <div className='vh-100 border border-3 border-success rounded'>
          <div className="h-75 overflow-auto border border-3 border-success rounded">
            <ListGroup className="w-100">
              <ListGroup.Item className="h-auto list-group-item-primary">
                <Col className="d-inline-block w-50 fw-bold">Title</Col>
                <Col className="d-inline-block w-50 fw-bold">Artist</Col>
              </ListGroup.Item>
              {
                posts.map((post) => (
                  !post.fileName.toLowerCase().endsWith('.wma') && post.title &&
                  (<ListGroup.Item className={"h-auto " + (num++ % 2 === 0 ? 'list-group-item-light' : 'list-group-item-dark')} key={post.id}>
                    <Col className="d-inline-block text-success w-50 playertext">{post.title}</Col>
                    <Col className="d-inline-block w-50">
                      <div className="d-flex flex-row">
                        <div className="flex-grow-1 text-warning text-truncate playertext"> {post.album}
                        </div>
                        <div className="p-l2">
                          <IconButton size='xs' circle icon={<MdPlayArrow />} onClick={() => { setSelectedSong(post.id); setTitle(post.title); setAlbum(post.album); downloadImage(post.id) }} />
                        </div>
                      </div>
                    </Col>
                  </ListGroup.Item>)
                ))
              }
            </ListGroup>
          </div>
          {selectedSong &&
            <Row>
              <Col className='col-10'>
                <AudioPlayer songId={selectedSong} songTitle={title} album={album} />
              </Col>
              <Col className='col-2'>
                <img className='img-thumbnail object-fit-contain border rounded' src={`data:image/jpeg;base64,${imageData}`} alt={title} />
              </Col>
            </Row>
          }
        </div>
      </Col>

    </Row>
  )
}

export default App
