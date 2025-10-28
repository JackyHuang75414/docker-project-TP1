# Project initialisation
## wsl
download wsl:\
`wsl --install
`\
and than, inside the wsl shell, we do the following steps
## Ansible
download Ansible:\
`sudo apt update && sudo apt upgrade
`\
`sudo apt install ansible -y
`\
`ansible --version
`\
download Apache2:\
`ansible webservers -m apt -a "name=apache2 state=present" --become 
`\
start the service:\
`ansible webservers -m service -a "name=apache2 state=started" --become
`\
`ansible-playbook -i inventories/setup.yml site.yml --ask-vault-pass
`
the password is 000
# 3-1 Inventory & basic common
### test link
- `ansible all -i inventories/setup.yml -m ping`
  - the right output need to be some thing like： 
```
{yourcloudname}.cloud | SUCCESS => {
"ansible_facts": {
"discovered_interpreter_python": "/usr/bin/python3"
},
"changed": false,
"ping": "pong"
}
```

### collect the system info
- `ansible all -i inventories/setup.yml -m setup -a "filter=ansible_distribution*"`
  - the right output need to be some thing like：
```
{yourcloudname}.cloud | SUCCESS => {
"ansible_facts": {
"ansible_distribution": "Debian",
"ansible_distribution_file_parsed": true,
"ansible_distribution_file_path": "/etc/os-release",
"ansible_distribution_file_variety": "Debian",
"ansible_distribution_major_version": "12",
"ansible_distribution_minor_version": "12",
"ansible_distribution_release": "bookworm",
"ansible_distribution_version": "12.12",
"discovered_interpreter_python": "/usr/bin/python3"
},
"changed": false
}
```


### Inventory setting
```yaml
all:
  vars:
    ansible_user: admin
    ansible_ssh_private_key_file: ./ssh_key
  children:
    prod:
      hosts:
        {yourcloudname}.cloud //you need to write your host 