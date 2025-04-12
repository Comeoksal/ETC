import requests

url = 'http://host3.dreamhack.games:14125/login'
data = {'username': '', 'password': ''}

response = requests.get(url)

cookies = response.cookies

print(response.text)
print(response)