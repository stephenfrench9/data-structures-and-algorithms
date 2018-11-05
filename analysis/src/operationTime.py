import argparse
import csv
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser(description='anaylze run times for various data structures')
parser.add_argument('input', metavar='h', type=str, nargs=1,
                    help='the raw data')
parser.add_argument('output', metavar='o', type=str, nargs=1,
                    help='the graph')
args = parser.parse_args()

print(args.input[0])
print(args.output[0])

# read and parse the prepared csv file
with open(args.input[0], newline='') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',')
    headerExists = True

    times = []
    for row in spamreader:
        if headerExists:
            header = row
            headerExists = False
            continue

        times.append(
            (float(row[0])/1000000, float(row[1])/1000)
        )



    plt.plot([x[0] for x in times], [x[1] for x in times])
    plt.title("Double Linked List \n get() time (ms) vs Array Size (N/1000")
    plt.xlabel("Arrays Size in thousands (N/1000")
    plt.ylabel("Time to perform get() operation (ms)")
    plt.savefig(args.output[0])
    plt.show()




