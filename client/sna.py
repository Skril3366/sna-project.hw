import datetime
from time import sleep
from systemd import journal
import requests


def process(journal: journal.Reader):
    for entry in journal:
        log_date = entry.get("__REALTIME_TIMESTAMP", datetime.datetime.now())
        log_host = entry.get("_HOSTNAME", "")
        log_process = entry.get("_CMDLINE", "")
        log_pid = entry.get("_PID", "")
        log_message = entry.get("MESSAGE", "")
        requests.post("http://localhost:9000/add", json={
            "date": log_date.strftime("%d-%m-%y %H:%M:%S.%f"),
            "host": log_host,
            "process": log_process,
            "pid": log_pid,
            "message": log_message})


if __name__ == '__main__':
    j = journal.Reader()
    j.seek_tail()
    while True:
        process(j)
        try:
            sleep(1)
        except KeyboardInterrupt:
            process(j)
            break
