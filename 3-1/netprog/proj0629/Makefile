C = gcc
TARGETS = server client
OBJS = common.o server.o client.o
all: $(TARGETS)
server: server.o common.o
        $(CC) -o server server.o common.o -lpthread
server.o: server.c common.h server.h
        $(CC) -c server.c
client: client.o common.o
        $(CC) -o client client.o common.o -lpthread
client.o: client.c common.h
        $(CC) -c client.c
common.o: common.c common.h
        $(CC) -c common.c
clean:
        rm -f $(TARGETS) $(OBJS)