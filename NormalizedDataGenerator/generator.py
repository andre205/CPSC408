import sys
import csv
import mimesis as m

#A game company database
#Storing user data and support tickets for problems with games
#
# UserInfo(uid, username, first, last, telephone,  address, zip, dob)
# UserInventory(uid, gid, purchasedate)
# UserSpecs(uid,cpu, cpu_freq, graphics, manf, ramsize, ramtype, resolution )
# GameInfo(gid, name, releasedate, description)
# UserTickets(tid, uid, gid)
# TicketInfo(tid, level)

#validate string as int
def strIsInt(s):
    try:
        int(s)
        return True
    except ValueError:
        return False

#ensure there are 3 input params, second is csv path, third is an integer
if len(sys.argv) != 3 or sys.argv[1][-4:] != '.csv' or not strIsInt(sys.argv[2]):
    print("Please use this program with the following command line parameters:")
    print("python generator.py [csv path] [tuples to generate]")
else:
    full_out = []

    for i in range(int(sys.argv[2])):
        int_out = []
        int_out.append(m.Person().username())
        int_out.append(m.Person().name())
        int_out.append(m.Person().last_name())
        int_out.append(m.Person().telephone())
        int_out.append(m.Address().address())
        int_out.append(m.Address().zip_code())
        int_out.append(m.Datetime().date())

        int_out.append(m.Datetime().date())

        int_out.append(m.Hardware().cpu())
        int_out.append(m.Hardware().cpu_frequency())
        int_out.append(m.Hardware().graphics())
        int_out.append(m.Hardware().manufacturer())
        int_out.append(m.Hardware().ram_size())
        int_out.append(m.Hardware().ram_type())
        int_out.append(m.Hardware().resolution())

        int_out.append(m.Games().game())
        int_out.append(m.Datetime().date())
        int_out.append(m.Text().text(2))

        int_out.append(m.Text().text(3))
        int_out.append(m.Text().level())

        full_out.append(int_out)

    with open(sys.argv[1], 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(full_out)
