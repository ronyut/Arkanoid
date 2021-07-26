compile: bin
	javac -cp biuoop-1.4.jar:src -d bin src/arkanoid/*.java

run:
	java -cp biuoop-1.4.jar:bin:resources Ass7Game

jar:
	jar cvfm ass7game.jar Manifest.mf -C bin . -C resources .

bin:
	mkdir bin