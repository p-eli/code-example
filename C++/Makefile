CC=g++
CFLAGS=-std=c++11 -Wall -pedantic -O2
LIBS=-lm
NAME=mul
OUTPUT=./outputs
INPUT=./input

all: *.cpp *.h
	$(CC) $(CFLAGS) $(LIBS) $^ -o $(NAME) 

debug: *.cpp *.h
	$(CC) -ggdb3 $(CFLAGS) $(LIBS) $^ -o $(NAME)

tests:  all
	@echo ""
	@rm -r $(OUTPUT) 2> /dev/null || true
	@mkdir $(OUTPUT)
	@echo "Conver picture with subsampling 4:2:0   $(INPUT)/lena_420.jpg --> $(OUTPUT)/lena_420.bmp"
	@./mul $(INPUT)/lena_420.jpg $(OUTPUT)/lena_420.bmp
	@echo "Conver picture with subsampling 4:2:2   $(INPUT)/lena_422.jpg --> $(OUTPUT)/lena_422.bmp"
	@./mul $(INPUT)/lena_422.jpg $(OUTPUT)/lena_422.bmp
	@echo "Conver picture with subsampling 4:4:4   $(INPUT)/lena_424.jpg --> $(OUTPUT)/lena_424.bmp"
	@./mul $(INPUT)/lena_444.jpg $(OUTPUT)/lena_444.bmp
	@echo "Convert picture to grayscale   $(INPUT)/lena_420.jpg --> $(OUTPUT)/lena_gray.bmp"
	@./mul $(INPUT)/lena_420.jpg $(OUTPUT)/lena_gray.bmp -bw
	@echo "Convert picture with descrease base function with 7/8 coefficients in idct   $(INPUT)/lena_420.jpg --> $(OUTPUT)/lena_7.bmp"
	@./mul $(INPUT)/lena_420.jpg $(OUTPUT)/lena_7.bmp -qt=7
	@echo "Convert picture with descrease base function with 4/8 coefficients in idct   $(INPUT)/lena_420.jpg --> $(OUTPUT)/lena_4.bmp"
	@./mul $(INPUT)/lena_420.jpg $(OUTPUT)/lena_4.bmp -qt=4
	@echo "Convert picture with descrease base function with 1/8 coefficients in idct   $(INPUT)/lena_420.jpg --> $(OUTPUT)/lena_1.bmp"
	@./mul $(INPUT)/lena_420.jpg $(OUTPUT)/lena_1.bmp -qt=1




clean:
	rm -f $(NAME)



