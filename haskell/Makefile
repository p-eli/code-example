all: main
main:
	ghc --make Main.hs -o subs-cipher
key:
	ghc --make Main.hs -o subs-cipher ;./subs-cipher -k ./test/threeChar.db ./test/test.in
plain:
	ghc --make Main.hs -o subs-cipher ;./subs-cipher -t ./test/threeChar.db ./test/test.in
clean:
	rm *.hi *.o subs-cipher
tests:
	ghc --make Main.hs -o subs-cipher ;sh test.sh