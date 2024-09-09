package com.learn.explorify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
class ExplorifyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void monoTest(){
		System.out.println(Thread.currentThread().getName());
		Mono<String> monoString=Mono.just("Om Ashish Soni").delaySubscription(Duration.ofSeconds(5));
		monoString.subscribe(output->{
			System.out.println(output+" , "+Thread.currentThread().getName());
		});
		StepVerifier.create(monoString)
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(4))
				.expectNext("Om Ashish Soni")
				.expectComplete()
				.verify(Duration.ofSeconds(6));
	}
}
