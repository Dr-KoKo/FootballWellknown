FROM gradle:7.5

COPY ./ ./

RUN gradle clean build --no-daemon

# 도커 컨테이너 외부에 노출할 포트 지정
EXPOSE 8080

# CMD와 유사하나, 디폴트로 수행될 명령은 ENTRYPOINT를 사용해 지정
CMD java -jar build/libs/*.jar

# 도커 timezone 변경
ENV TZ=Asia/Seoul
