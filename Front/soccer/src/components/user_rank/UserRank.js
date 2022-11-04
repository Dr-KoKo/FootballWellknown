import React, { useEffect, useState } from "react";
import Loading from "components/Loading";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import './UserRank.css';

const UserRank = () => {

    useEffect(() => {
        setUserRank(data);
    },[])

    const [userRank, setUserRank] = useState([]);

    const data = [
        {nickname: "귀여운 너구리", point: 10},
        {nickname: "아찔한 악어", point: 2},
        {nickname: "재빠른 나무늘보", point: 16},
        {nickname: "무시무시한 닥스훈트", point: 4},
        {nickname: "먹보 여우", point: 15},
    ]

    // slider 세팅
    const settings = {
        dots: false,
        vertical: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
      };

    return (
        <>
        {userRank.length > 0 ? 
        // 데이터 받아오면 렌더링
            <div className="rankSlider">
            <Slider  {...settings}>
                {userRank.map((item,i) => 
                    <div key={i} >
                        <span style={{display:'inline-block', width:'30px'}}>{`${i+1}위`}</span>
                        <span style={{display:'inline-block', fontWeight: 'bold',width:"250px"}}>
                            {`${item.nickname}`}
                        </span>
                        <span style={{display:'inline-block', fontWeight: 'bold', width:'30px',textAlign:'end'}}>
                            {item.point}
                        </span>
                        <span> pts</span>
                    </div>
                )}
            </Slider>
            <div id="rankBoard">
            {userRank.map((item,i) => 
                    <div key={i} style={{marginBottom:'0.2rem'}}>
                        <span style={{display:'inline-block', width:'30px'}}>{`${i+1}위`}</span>
                        <span style={{display:'inline-block', fontWeight: 'bold',width:"250px"}}>
                            {`${item.nickname}`}
                        </span>
                        <span style={{display:'inline-block', fontWeight: 'bold', width:'30px', textAlign:'end'}}>
                            {item.point}
                        </span>
                        <span> pts</span>
                    </div>
                )}
                </div>
            </div>
        :
        // 데이터 못 받으면 로딩
        <Loading />}
        </>
    )
}

export default UserRank;