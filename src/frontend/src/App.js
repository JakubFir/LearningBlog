import { Layout} from 'antd';
import {Route, Routes } from 'react-router-dom';
import BlogPosts from './pages/BlogPosts';
import {AboutMe} from './pages/AboutMe';
import {Contact} from './pages/Contact';
import {Home} from './pages/Home';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { BlogNavi } from './pages/BlogNavi';

const {Content } = Layout;

function App() {
    return (
        <Layout>
            <BlogNavi />
            <Content>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/posts" element={<BlogPosts />} />
                    <Route path="/about-me" element={<AboutMe />} />
                    <Route path="/contact" element={<Contact />} />
                </Routes>
            </Content>
        </Layout>
    );
}

export default App;