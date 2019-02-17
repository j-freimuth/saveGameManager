FROM 'hseeberger/scala-sbt'

RUN useradd -u 1000 -U -s /bin/bash developer && \
		[ -d /srv/www ] || mkdir /srv/www 
	
USER developer
	
WORKDIR /srv/www

CMD sbt


