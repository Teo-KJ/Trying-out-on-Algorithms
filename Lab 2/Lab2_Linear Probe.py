import time
import pandas as pd

def create_hashTable(size):
    table = []
    for i in range(0, size):
        table.append(list())
    return table

def hash_function(key, size):
    return key % size

def insert_to_hashTable(table, Dataset_ID, Dataset_Value, size):
    key = hash_function(Dataset_ID, size)

    if len(table[key])==0:
        table[key].append(Dataset_Value)
    else:
        while len(table[key])!=0:
            key = (key+1)%size
        table[key].append(Dataset_Value)

def search_hashtable(table, searchID, item, size):
    key = searchID % size
    if table[key] == [item]:
        return key
    else:
        count = 1
        while table[key]!= [item] and count <= size:
            key = (key+1)%size
            count += 1
        else:
            if count > size:
                return "Not found"
            return key

print("Welcome to my hash table! Please indicate the load factor (n/h) \nChoose the option.")
print("1. (n/h) = 0.25 \n2. (n/h) = 0.5 \n3. (n/h) = 0.75")

load_factor = int(input())
if load_factor == 1:
    size = 801
elif load_factor == 2:
    size = 401
elif load_factor == 3:
    size = 271
else:
    size = 200

print("Loading dataset...")
data = pd.read_csv('employees-200.csv')


print("Creating hash table...")

my_table = create_hashTable(size)

start_hash = time.perf_counter()

for a, b in data.itertuples(index=False):
    a = int(a)
    b = int(b)
    insert_to_hashTable(my_table, a, b, size)

end_hash = time.perf_counter()
print(my_table)

print("Completed")

searchID = int(input("Please enter ID to search. "))

for i in data['employee_id']:
    if i == searchID:
        search_value = data[data['employee_id'] == searchID]
        value = search_value['annual_inc'].reset_index()
        value = value.at[0, 'annual_inc']
        break
    else:
        value = 0

start_search = time.perf_counter()
result = search_hashtable(my_table, searchID, value, size)
end_search = time.perf_counter()

print("Location:", result)
print("id: ", searchID, "and income: ", value)

print("The total runtime for hashing ", round(end_hash - start_hash, 6), "sec.")
print("The total runtime for searching ", round(end_search - start_search, 6), "sec.")

total_time = 0

for j in data['employee_id']:
    access = j
    start_time = time.perf_counter()
    search_value = data[data['employee_id'] == access]
    value = search_value['annual_inc'].reset_index()
    value = value.at[0, 'annual_inc']
    search_hashtable(my_table, access, int(value), size)
    end_time = time.perf_counter()
    total_time += end_time - start_time

print("Average time for searches: ", total_time/200)
